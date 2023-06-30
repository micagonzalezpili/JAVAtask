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
      loggedOut: true
      
       
    };
  },
  created() {
    this.loadData();
   
},
methods: {  
loadData(){          
    axios.get('http://localhost:8080/api/clients/current')
    .then(response => {
        this.data = response.data
        console.log(this.data);               
        this.transactions = this.data.accounts[0].transactions
        console.log(this.transactions);
        this.transactions.forEach(transaction => transaction.time = transaction.date.slice(11,19) )          
        this.transactions.forEach(transaction => transaction.date = transaction.date.slice(0,10) )       
        this.loggedIn = true   
        this.loggedOut = false  
    })
    .catch(error => {
        console.error(error);
      });
},
logOut() {
  axios.post('/api/logout')
    .then(response => {
      console.log('Signed out!!');
      window.location.href = '/index.html';
    })
    .catch(error => {
      console.error('Error', error);
    });
} 
  
}})
app.mount('#app');