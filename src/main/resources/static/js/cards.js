const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      data: [],               
      cards: [],
      debitCards: [],
      creditCards: []
      
       
    };
  },
  created() {
    this.loadData();
   
},
methods: {
loadData(){              
    axios.get('http://localhost:8080/api/clients/1')
    .then(response => {      
        this.data = response.data
        console.log(this.data); 
        this.cards = this.data.cards
        console.log(this.cards);
        this.cards.forEach(card => card.thruDate = card.thruDate.slice(0,7) )    
        this.cards.forEach(card => card.fromDate = card.fromDate.slice(0,7) )         
        this.creditCards = this.cards.filter(card => card.type === 'CREDIT')
        console.log(this.creditCards);
        this.debitCards = this.cards.filter(card => card.type === 'DEBIT')
        console.log(this.debitCards);
        
    })
    .catch(error => {
        console.error(error);
      });
}
  
}})
app.mount('#app');