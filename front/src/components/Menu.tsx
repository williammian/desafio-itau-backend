import { Navbar, Nav, Container } from 'react-bootstrap';
import { PAGE } from '../constants';

const Menu: React.FC = () => {
  return (
    <Navbar bg="dark" variant="dark" expand="lg" className="mb-1">
      <Container>
        <Navbar.Brand href={PAGE.ROOT}>Meu App</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="flex-column flex-lg-row">
                <Nav.Link href={PAGE.TRANSACAO} className="py-1">Nova Transação</Nav.Link>
                <Nav.Link href={PAGE.ESTATISTICA} className="py-1">Estatísticas</Nav.Link>
            </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Menu;
