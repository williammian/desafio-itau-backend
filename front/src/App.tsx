import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import TransacaoForm from "./components/TransacaoForm";
import EstatisticaCard from './components/EstatisticaCard';
import Menu from './components/Menu';
import { PAGE } from './constants';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

const App: React.FC = () => {
  return (
    <>
    <Menu />
      <Router>
        <Routes>
          <Route path={PAGE.TRANSACAO} element={<TransacaoForm />} />
          <Route path={PAGE.ESTATISTICA} element={<EstatisticaCard />} />
        </Routes>
      </Router>
    </>
  );
};

export default App;
