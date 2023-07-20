const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      data: [],               
      cards: [],
      debitCards: [],
      creditCards: [],
      loggedOut: false,
      loggedIn: true,
      expiredCards: [],
      actualDate: 0,
      activeCards: []
      
      
       
    };
  },
  created() {
    this.loadData();
    
   
},
methods: {
  loadData() {
    axios.get('http://localhost:8080/api/clients/current')
      .then(response => {
        this.data = response.data;
        console.log(this.data);
        this.cards = this.data.cards;
        console.log(this.cards);
        this.activeCards = this.cards.filter(card => card.active == true);
        console.log(this.activeCards);
        this.creditCards = this.cards.filter(card => card.type === 'CREDIT' && card.active == true);
        console.log(this.creditCards);
        this.debitCards = this.cards.filter(card => card.type === 'DEBIT' && card.active == true);
        console.log(this.debitCards);

        this.cards.forEach(card => {
          const date = new Date(card.thruDate);
          const year = date.getFullYear();
          let month = (date.getMonth() + 1).toString().padStart(2, '0');
          card.thruDate = `${month}-${year}`;
        });

        this.actualDate = new Date();
        const year = this.actualDate.getFullYear();
        let month = (this.actualDate.getMonth() + 1).toString().padStart(2, '0');
        this.actualDate = `${month}-${year}`;
      })
      .catch(error => {
        console.error(error);
      });
  },
deleteCard(id) {
  axios.patch(`/api/clients/current/cards/${id}`)
    .then(response => {      
      console.log('card deleted!!');
      this.loadData(); 
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