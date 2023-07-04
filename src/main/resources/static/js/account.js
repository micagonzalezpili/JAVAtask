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
  this.params = new URLSearchParams(location.search)
  this.dataParams = this.params.get("id")  
  console.log(this.dataParams); 
   
axios.get('http://localhost:8080/api/accounts/'+this.dataParams)
.then(response => {
   this.accountID = this.data.find(acc => acc.id == this.dataParams)
   this.data = response.data
   console.log(this.data);             
   this.transactions = this.data.transactions.sort()
   console.log(this.transactions);
    this.transactions.forEach(transaction => transaction.time = transaction.date.slice(11,19) )          
   this.transactions.forEach(transaction => transaction.date = transaction.date.slice(0,10) )        
   
   
   
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