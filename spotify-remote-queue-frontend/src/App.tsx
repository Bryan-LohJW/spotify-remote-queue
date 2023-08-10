import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import Home from './pages/home/Home';

const router = createBrowserRouter([
	{
		element: <Home />,
		path: '/',
	},
]);

const App = () => {
	return <RouterProvider router={router} />;
};

export default App;
