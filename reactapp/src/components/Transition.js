import React, { useEffect, useState } from "react";
import axios from 'axios';
import { IMaskInput } from "react-imask";
import Swal from "sweetalert2";
import { Alert } from "bootstrap";


const Transition = () => {
  const [selectedPerson, setSelectedPerson] = useState("Notselected");
  const [accountsPerson, setAccountsPerson] = useState([]);
  const [valueTransition ,setValueTransition] = useState("");
  const [accountSelected, setAcountSelected] = useState("");
  const [typeTransition, setTypeTransition] = useState("deposit");
  const [persons, setPersons] = useState([]);
  const [transactions, setTransactions] = useState([]);
  const [balance, setBalance] = useState ([]);


function getTransactions(){
  axios.get('http://127.0.0.1:8080/transactions/'+ accountSelected).then(
    response => {
      setTransactions(response.data);
      // let balanced = 0;
      // response.data.map(x =>{
      //   balanced = balanced + x.value;
        
      // })
      // setBalance(balanced);

}
).catch(
error =>{
    console.log(error);
}
)
}

function selectPerson(e){
  setSelectedPerson(e.target.value);
  let personId = e.target.value;
  getAccountInformations(personId);
  setTransactions([]);
}

function getAccountInformations(personId){
  axios.get('http://127.0.0.1:8080/accounts/person/'+ personId).then(
    response => {
      console.log(response.data);
      setAccountsPerson(response.data);
      getTransactions();
      balanceTransalation(response.data);
     
}
).catch(
error =>{
    console.log(error);
}
)
}
 function makeTransition(){
  if(selectedPerson == "Notselected" || accountsPerson == "Notselected" || valueTransition == ""|| accountSelected =="" || valueTransition == "0"){
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: 'Existem campos sem preencher!',
    })
  }else{
    if(typeTransition == "deposit"){
      const deposit = {
        "value":valueTransition,};
        axios.put('http://127.0.0.1:8080/accounts/deposit/' + accountSelected ,deposit)
        .then(response => {
          setValueTransition("");
          getAccountInformations(accountSelected);
          getTransactions();
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Operação realizada com sucesso!',
                showConfirmButton: false,
                timer: 1500
              })
        })
        .catch(error =>{
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Ocorreu um erro na operação!',
              })
            console.log(error);
        })
    }else if(typeTransition == "cashout"){
      const deposit = {
        "value":valueTransition,};
        axios.put('http://127.0.0.1:8080/accounts/cashout/' + accountSelected ,deposit)
        .then(response => {
          setValueTransition("");
          getAccountInformations(accountSelected);
          getTransactions();
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Operação realizada com sucesso!',
                showConfirmButton: false,
                timer: 1500
              })
        })
        .catch(error =>{
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: error.response.data.message,
              })
            console.log(error);
        })
    }

  }
 }

 function balanceTransalation(contas){
      contas.map(x => {
        if(x.id == accountSelected){
          setBalance(x.balance);
        }
      })

 }

useEffect((e) =>{
  getTransactions();
  getAccountInformations(accountSelected);
},[accountSelected])



useEffect(()=>{

axios.get('http://127.0.0.1:8080/persons').then(
response => {
    setPersons(response.data);
}
).catch(
error =>{
    console.log(error);
}
)
},[])
   
    return(
<>
<div >
<h3 className="text-start mr-5 ml-5">Cadastro de Movimentação</h3>
    <div className="mr-5 ml-5 row">
    <div class="mb-3 mt-3 input-group">


    <label className="col-sm-2 col-form-label">Pessoa:</label>
  <div class="col-sm-6">

<select class="form-select" aria-label="Default select example"
 onChange={e => selectPerson(e)}
>
  <option value="Notselected">Selecione uma pessoa</option>
  {persons.map(person =>(
    <>
<option key={person.id} value={person.id}>{person.name} - {person.cpf}</option> 
</>
  ))}
</select>
  </div>
  </div>
  
  <div class="mb-3 mt-3 input-group">
  <label className="col-sm-2 col-form-label">Conta:</label>
  <div class="col-sm-6">

<select class="form-select" aria-label="Default select example"
 onChange={e => {
  setAcountSelected(e.target.value)
  }}
 >
  <option value="Notselected">Selecione uma conta</option>
  {accountsPerson.map(account =>(
<>
<option key={account.id} value={account.id}>{account.number} - {account.balance.toLocaleString('pt-BR', {
    style: 'currency',
    currency: 'BRL',
  })}</option> 
</>
  ))}
</select>
  </div>
  </div>


  <div class="mb-3 mt-3 input-group">
    <label for="valueTransition" className="col-sm-2 col-form-label">Valor:</label>
    <div className="col-sm-6">
    <IMaskInput
  mask=""
    type="number" class="form-control" id="valueTransition" value={valueTransition} onChange={e => setValueTransition(e.target.value)}/>

    </div>
  </div>

  <div class="mb-3 mt-3 input-group">
  <label className="col-sm-2 col-form-label">Conta:</label>
  <div class="col-sm-6">

<select class="form-select" aria-label="Default select example"
 onChange={e => setTypeTransition(e.target.value)}
 >
  <option value="deposit">Depositar</option>
  <option value="cashout">Sacar</option>

</select>
  </div>
  </div>

  <div className="text-end col-sm-8">
  <button class="btn btn-primary" onClick={makeTransition}>Realizar Operação</button>
  </div>
    </div>

</div>

<hr className="bg-dark"/>

<div className="mr-5 ml-5 row">
<h3 className="text-start m-5">Contas Cadastradas</h3>
<div className="col-sm-8">

<table class="table">
  <thead>
    <tr>
      <th scope="col">Data</th>
      <th scope="col">Valor</th>

    </tr>
  </thead>
  <tbody>
  {transactions.map(transaction =>(

<tr key={transaction.id} className={transaction.value < 0 && ("bg-danger")}>
      <td>{transaction.instant.substring(0,19).replace("T", " ")}</td>
      <td >{transaction.value.toLocaleString('pt-BR', {
    style: 'currency',
    currency: 'BRL',
  })}</td>
    </tr>

  ))}
  </tbody>
</table>
<div className="text-end col-sm-10">
<h5>
Saldo: {balance.toLocaleString('pt-BR', {
    style: 'currency',
    currency: 'BRL',
  })}
</h5>
</div>
</div>


</div>


</>


    )
}

export default Transition;