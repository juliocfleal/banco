import React, { useEffect, useState } from "react";
import axios from 'axios';
import Swal from "sweetalert2";


const Account = () => {
const [persons, setPersons] = useState([]);
const [accounts, setAccounts] = useState([]);
const [accountNumber, setAccountNumber] = useState("");
const [selectedPerson, setSelectedPerson] = useState("Notselected");
const [accountNameModal, setAccountNameModal]= useState("");
const [accountNumberModal, setAccountNumberModal] = useState("");
const [accountIdModal, setAcccountIdModal] = useState ("");

function deleteAccount(id, name){
  Swal.fire({
      title: 'Tem certeza que deseja excluir a conta do titular '+ name+ '?',
      showCancelButton: true,
      confirmButtonText: 'Excluir',
    }).then((result) => {
      if (result.isConfirmed) {
          axios.delete('http://127.0.0.1:8080/accounts/' + id)
          .then(response => {
              console.log(response);
              Swal.fire('Excluido!', '', 'success');
              getAccounts();
          }).catch( error =>{
              console.log(error);
      });
      }
    })
}

function updateAccount(){
  if(!accountNumberModal){
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: 'Insira um numero de conta!',
    })
  }else{
    const accountUpdate = {
      "number": accountNumberModal
    } 
    axios.put('http://127.0.0.1:8080/accounts/' + accountIdModal, accountUpdate)
    .then(response => {       
      getAccounts();     
      Swal.fire({
      position: 'top-end',
      icon: 'success',
      title: 'Conta atualizada com sucesso!',
      showConfirmButton: false,
      timer: 1500
    })
    }).catch(error =>{
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: error.response.data.message,
      })
        console.log(error);
    });
  }
}

function showModal(id){
  axios.get('http://127.0.0.1:8080/accounts/' + id)
  .then(response => {
    setAcccountIdModal(id);
    setAccountNameModal(response.data.personDTO.name);
    setAccountNumberModal(response.data.number);
      console.log(response);
  }).catch(error =>{
      console.log(error);
  });
}

function saveAccount(){
  if(selectedPerson == "Notselected"){
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: 'Selecione uma pessoa!',
    })
  }else if(!accountNumber){
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: 'Insira um numero de conta!',
    })
  }else{
    const account = {
      "number":accountNumber.replace(/\D/g, ""),
    "personId": selectedPerson};
        axios.post('http://127.0.0.1:8080/accounts',account)
        .then(response => {
            getAccounts();
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Conta cadastrada com sucesso!',
                showConfirmButton: false,
                timer: 1500
              })
              setAccountNumber("");
        }).catch(error =>{
          getAccounts();
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: error.response.data.message,
          })
            console.log(error);
        });
  }
}

function getAccounts(){
  axios.get('http://127.0.0.1:8080/accounts').then(
    response => {
        setAccounts(response.data);
        console.log(response.data);
    }
).catch(
    error =>{
        console.log(error);
    });
}

    useEffect(()=>{
      getAccounts();
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
<h3 className="text-start mr-5 ml-5">Cadastro de Conta</h3>
    <div className="mr-5 ml-5 row">

  <label className="col-sm-2 col-form-label">Pessoa:</label>
  <div class="col-sm-6">

<select class="form-select" aria-label="Default select example" onChange={e => setSelectedPerson(e.target.value)}>
  <option value="Notselected">Selecione uma pessoa</option>
  {persons.map(person =>(
<>
<option key={person.id} value={person.id}>{person.name} - {person.cpf}</option> 
</>
  ))}
</select>
  </div>

  <div class="mb-3 mt-3 input-group">
    <label for="accountNumber" className="col-sm-2 col-form-label">Numero da Conta </label>
    <div className="col-sm-6">
    <input type="number" class="form-control" id="accountNumber" value={accountNumber} onChange={e => setAccountNumber(e.target.value)}/>

    </div>
  </div>
  <div className="text-end col-sm-8">
  <button class="btn btn-primary" onClick={saveAccount}>Salvar</button>
  </div>
    </div>
</div>

<hr className="bg-dark"/>

<div className="mr-5 ml-5 row">
<h3 className="text-start m-5">Contas Cadastradas</h3>
<div className="col-sm-6">

<table class="table">
  <thead>
    <tr>
      <th scope="col">Nome</th>
      <th scope="col">CPF</th>
      <th scope="col">Numero da Conta</th>
      <th scope="col"></th>
      <th scope="col"></th>
    </tr>
  </thead>
  <tbody>
  {accounts.map(account =>(

<tr key={account.id}>
      <td>{account.personDTO.name}</td>
      <td>{account.personDTO.cpf}</td>
      <td>{account.number}</td>
      <td><button className="btn btn-warning" data-bs-toggle="modal" data-bs-target="#staticBackdrop" onClick={() => showModal(account.id)}>Editar</button></td>
      <td><button className="btn btn-danger" onClick={() => deleteAccount(account.id, account.personDTO.name)}>Remover</button></td>
    </tr>

  ))}
  </tbody>
</table>
</div>


</div>



<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="staticBackdropLabel">Editar Conta</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">




      <div className="mr-5 ml-5 row">
  <div class="mb-3 mt-3 input-group">
    <label for="name" className="col-sm-4 col-form-label">Nome do titular:</label>
    <div className="col-sm-8">
    <input type="text" className="form-control" id="name" value={accountNameModal} disabled/>
    </div>
  </div>

  <div class="mb-3 mt-3 input-group">
    <label for="accountNumber" className="col-sm-4 col-form-label">Numero da conta:</label>
    <div className="col-sm-8">
    <input
     className="form-control" id="accountNumber" value={accountNumberModal} onChange={e => setAccountNumberModal(e.target.value)}/>
    </div>
  </div>

    </div>



      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
        <button type="button" class="btn btn-primary" data-bs-dismiss="modal" onClick={updateAccount}>Salvar Alterações</button>
      </div>
    </div>
  </div>
</div>

</>


    )
}

export default Account;