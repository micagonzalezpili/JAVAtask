const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      data: [],     
      transactions: [],      
      accountID: [],
       
    };
  },
  created() {
    this.loadData();
   
},
methods: {
loadData(){
    axios.get('http://localhost:8080/api/accounts') // ver!!!!
    .then(response => {
        this.data = response.data
        console.log(this.data);       
        this.params = new URLSearchParams(location.search)
        this.dataParams = this.params.get("id")        
        this.accountID = this.data.find(acc => acc.id == this.dataParams) 
        console.log(this.accountID);
        this.transactions = this.accountID.transactions
        console.log(this.transactions);
        this.transactions.forEach(transaction => transaction.time = transaction.date.slice(11,19) )          
        this.transactions.forEach(transaction => transaction.date = transaction.date.slice(0,10) )        
        
        
    })
    .catch(error => {
        console.error(error);
      });
}
  
}})
app.mount('#app');