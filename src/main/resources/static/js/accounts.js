
const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      data: [],     
      nombre: "",
      apellido: "",   
      accounts: [],
      loans: []
      
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
         this.nombre = this.data.firstName         
         this.apellido = this.data.lastName         
         this.accounts = this.data.accounts
         console.log(this.accounts);
         this.loans = this.data.clientLoans
         console.log(this.loans);     
         })
         .catch(error => {
           console.error(error);
         });
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
}})
app.mount('#app');