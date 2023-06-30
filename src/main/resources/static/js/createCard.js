const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      data: [],          
      type: "",
      color: "" ,
      loggedIn: true   
      
      
       
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
    })
    .catch(error => {
        console.error(error)
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
},

createCard(){
    console.log(this.color, this.type);
axios.post('/api/clients/current/cards', 'color='+ this.color + '&type=' + this.type )
.then(response => {
    console.log('card created!!');
    window.location.href = '/web/cards.html'
})
.catch(error => {
    console.log(error);
    alert(error.response.data)
})
}
  
}})
app.mount('#app');