const { createApp } = Vue;

const app = createApp({
    data() {
        return {
            data: [],
            nombre: "",
            apellido: "",
            accounts: [],
            loggedIn: true,
            loans: "",
            payments: "",
            loanSelected: null,
            selectedPayment: null,
            amount: 0,
            destinAcc: "",
            maxAmount: 0,
            loan: {
                "id": this.loanSelected,
                "name": this.loanSelected,
                "payment": this.selectedPayment,
                "amount": this.amount,
                "accountDestin": this.destinAcc
            },
            maxAmount: 0,
            interest: 0,
            total: 0
        };
    },
    created() {
        this.loadData();
        this.getLoans();

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
        getLoans() {
            axios.get('/api/loans')
                .then(response => {
                    this.loans = response.data
                    console.log(this.loans);
                    this.payments = this.loans.map(loan => loan.payments)
                    console.log(this.payments);
                    this.maxAmount = this.loans.map(loan => loan.maxAmount)
                    console.log(this.maxAmount);
                    //this.total = this.loanSelected.forEach(loan => loan.amount * loan.percentage);
                })
                .catch(error => {
                    console.log(error);
                })
        },
        getSelectedLoanPayments() {
            if (this.loanSelected) {
                return this.loanSelected.payments;
            }
            return [];
        },
        applyLoan() {           
            axios.post('/api/loans', {
                id: this.loanSelected.id,
                name: this.loanSelected.name,
                payment: this.selectedPayment,
                amount: this.amount,
                accountDestin: this.destinAcc
              })
          .then(response =>{
            console.log("applied!!");
          })
          .catch(error => {
            console.log(error);
            Swal.fire(
                'Oops..',
                `${error.response.data} `,
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
                        'Applied succesfully!',
                        'Thanks for operating with CashFlow.',
                        'success',
                        this.applyLoan()
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
            /* .catch(error => {
                Swal.fire(
                    'Oops..',
                    `${error.response.data} Please try again.`,
                    'error'
                  )
            }) */
        },
        getMaxAmount() {
            if (this.loanSelected != null) {
                const loan = this.loans.find(loan => loan.id === this.loanSelected);
                if (loan) {
                    return `Max Amount: $${loan.maxAmount}`;
                }
            }
            return "";
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