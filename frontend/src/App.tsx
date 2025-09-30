import { Header } from "./components/Header";
import { Board } from "./pages/Board";
import { TasksProvider } from './contexts/TasksContext';

export function App() {
  return (
    <TasksProvider>
      <div className="wrapper">
        <Header />
        <Board />
      </div>
    </TasksProvider>
  )
}

