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
   /* this.clientID = this.data.client._links.client.href
   console.log(this.clientID); */
},
methods: {
loadData(){
axios.get('http://localhost:8080/api/clients/1')
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
}
  
}})
app.mount('#app');