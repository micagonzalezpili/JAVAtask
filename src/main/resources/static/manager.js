const { createApp } = Vue;

const app = createApp({
  data() {
    return {
      data: [],
      clients: [],
      clientData: {firstName: "", lastName: "", email: ""},
      clientID: ""

    };
  },
  created() {
    this.loadData();
   /* this.clientID = this.data.client._links.client.href
   console.log(this.clientID); */
},
methods: {
loadData(){
axios.get('http://localhost:8080/clients')
         .then(response => {
         this.data = response.data
           console.log(this.data);
         this.clients = this.data._embedded.clients
            console.log(this.clients)
         })
         .catch(error => {
           console.error(error);
         });
},
addClient(){
this.postClient()
},
postClient(){
axios.post('http://localhost:8080/clients', this.clientData)
.then(response => {
    this.loadData()
})
.catch(error => {
           console.error(error);
         });
},
deleteClient(id){
axios.delete(id)
    .then(response => {
      this.loadData();
    })
    .catch(error => {
      console.error(error);
    });
},
modifyClient(){
  axios.patch('http://localhost:8080/clients', this.clientData, firstName)
  .then(response => {
    this.loadData()
  })
  .catch(error => {
    console.error(error)
  })
}
}
})
app.mount('#app');





