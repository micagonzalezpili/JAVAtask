const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      data: [],
      transactions: [],
      accountID: [],
      params: [],
      dataParams: [],
      loggedIn: false,
      loggedOut: true,
      currentBalance: 0,
      activeTransactions: [],
      startDate: null,
      endDate: null

    };
  },
  created() {
    this.loadData();
  },
  methods: {
    precioFormat(number) {
      USDollar = new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
      });
      return USDollar.format(number);
    },
   
    loadData() {
      this.params = new URLSearchParams(location.search);
      this.dataParams = this.params.get('id');
      console.log(this.dataParams);    
      axios
        .get('/api/accounts/' + this.dataParams)
        .then((response) => {
          this.accountID = this.data.find((acc) => acc.id == this.dataParams);
          this.data = response.data;
          console.log(this.data);
          this.transactions = this.data.transactions.sort((a, b) => new Date(b.date) - new Date(a.date));
           console.log(this.transactions);
          this.transactions.forEach((transaction) => (transaction.time = transaction.date.slice(11, 19)));
          this.transactions.forEach((transaction) => (transaction.date = transaction.date.slice(0, 10)));
          this.activeTransactions = this.transactions.filter(transaction => transaction.active == true)
          this.activeTransactions = this.activeTransactions.sort((a, b) => new Date(b.date) - new Date(a.date));
          console.log(this.activeTransactions);
          this.currentBalance = this.activeTransactions.filter(transaction => transaction.balance)
          console.log(this.currentBalance);
        
    
        })
        .catch((error) => {
          console.error(error);
        });
    },
    getCurrentBalance(transaction) {
      return this.data.balance - transaction.amount;
    },
    getTransactionsByDate(){
      axios.post('/api/filter/transactions', 'id=' + this.dataParams + '&startDate=' + this.startDate + '&endDate=' + this.endDate, {
        responseType: 'arraybuffer', // Indica que la respuesta debe tratarse como un arreglo de bytes
      })
      .then(response => {
        console.log('transactions filtered!');
        const pdfBlob = new Blob([response.data], { type: 'application/pdf' });
        const url = URL.createObjectURL(pdfBlob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'transactions.pdf';
        a.click();
        URL.revokeObjectURL(url); // Liberar el objeto URL
      })
      .catch(error => {
        console.log(error);
        Swal.fire(
          'Oops..',
          'Please try again. Make sure both fields are correct.',
          'error'
        )
      })
    },
    
    logOut() {
      axios
        .post('/api/logout')
        .then((response) => {
          console.log('Signed out!!');
          window.location.href = '/index.html';
        })
        .catch((error) => {
          console.error('Error', error);
        });
    },
  },
});

app.mount('#app');
