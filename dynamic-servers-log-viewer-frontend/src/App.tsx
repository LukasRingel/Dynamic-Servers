import './App.css';
import {BrowserRouter as Router} from 'react-router-dom';
import ApplicationLayout from "./application/layout/ApplicationLayout";

function App() {
  return (
    <Router>
      <ApplicationLayout/>
    </Router>
  );
}

export default App;