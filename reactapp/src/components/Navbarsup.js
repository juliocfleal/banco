import React from "react";
import { Link } from "react-router-dom";

const Navbarsup = () =>{

    return(
        <>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
      <div class="navbar-nav">
        <Link class="nav-link" aria-current="page" to="/">Pessoa</Link>
        <Link class="nav-link" to="/account">Conta</Link>
        <Link class="nav-link" to="/transition">Movimentação</Link>
      </div>
    </div>
  </div>
</nav>
        </>
    )
}

export default Navbarsup;