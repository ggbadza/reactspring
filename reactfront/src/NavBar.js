import { Container,Nav,Navbar } from 'react-bootstrap';
import { getCookie,removeCookie } from './User/cookies';

function NavBar(){
  const onClickLogout = () => {
    removeCookie("Authorization");
  }
    return (
        <Navbar bg="light" variant="light">
        <Container>
          <Navbar.Brand href="/">TANKMILU</Navbar.Brand>
          <Nav className="me-auto">
            <Nav.Link href="#home">Home</Nav.Link>
            <Nav.Link href="#features">Features</Nav.Link>
          </Nav>
          <Nav>
          {getCookie("Authorization") ? (
            <Nav.Link href="/" onClick={onClickLogout}>로그아웃</Nav.Link>
          ) : (
            <Nav.Link href="/login">로그인</Nav.Link>
          )}
          </Nav>
        </Container>
      </Navbar>
    );
}

export default NavBar;