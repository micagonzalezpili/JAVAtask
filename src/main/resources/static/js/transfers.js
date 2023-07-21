const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      data: [],
      nombre: "",
      apellido: "",
      accounts: [],
      loggedIn: true,
      own: "",
      other: "",
      description: "",
      amount: 0,
      accChosen: "",
      accountSelected: "3",
      arrayAccounts: []

    };
  },
  created() {
    this.loadData();

  },
  methods: {
    loadData() {
      axios.get('/api/clients/current')
        .then(response => {
          this.data = response.data
          console.log(this.data);
          this.nombre = this.data.firstName
          this.apellido = this.data.lastName
          this.accounts = this.data.accounts
          console.log(this.accounts);
          this.arrayAccounts = this.accounts.map(account => account.number)
          console.log(this.arrayAccounts);
        })
        .catch(error => {
          console.error(error);
        });
    },
    createTransfer() {
      console.log(this.accChosen);
      console.log(this.accountSelected);      
      console.log('amount=' + this.amount + '&description=' + this.description + '&originAcc=' + this.accountSelected + '&destinAcc=' + this.accChosen);
      axios.post('/api/transactions', 'amount=' + this.amount + '&description=' + this.description + '&originAcc=' + this.accountSelected + '&destinAcc=' + this.accChosen, 
       { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
        .then(response => {
          console.log("Transaction made!!!");
          window.location.href= '/web/accounts.html'
        })
        .catch(
          error => {
            console.log(error);
            Swal.fire(
              'Oops..',
              `${error.response.data} Please try again.`,
              'error'
            )
          })
    },
    confirmOperation() {
      const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
          confirmButton: 'btn btn-success',
          cancelButton: 'btn btn-danger'
        },
        buttonsStyling: false
      })

      swalWithBootstrapButtons.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, transfer.',
        cancelButtonText: 'No, cancel.',
        reverseButtons: true
      }).then((result) => {
        if (result.isConfirmed) {
          swalWithBootstrapButtons.fire(
            'Transaction succesfully made!',
            'Thanks for operating with CashFlow.',
            'success',
            this.createTransfer()
          )
        } else if (
          /* Read more about handling dismissals below */
          result.dismiss === Swal.DismissReason.cancel
        ) {
          swalWithBootstrapButtons.fire(
            'Cancelled',
            'No transaction made :)',
            'error'
          )
        }
      })
    },
    logOut() {
      console.log("hola");
      axios.post('/api/logout')
        .then(response => {
          console.log('Signed out!!');
          window.location.href = '/index.html';
        })
        .catch(error => {
          console.error('Error', error);
        });
    }
  }
})
app.mount('#app');