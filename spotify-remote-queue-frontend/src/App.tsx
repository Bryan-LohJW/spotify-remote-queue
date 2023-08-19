import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import Home from './pages/home/Home';
import SpotifyResponse from './pages/spotify-response/SpotifyResponse';
import { QueryClient, QueryClientProvider } from 'react-query';

const router = createBrowserRouter([
	{
		element: <Home />,
		path: '/',
	},
	{
		element: <SpotifyResponse />,
		path: '/spotify-response',
	},
]);

const queryClient = new QueryClient();

const App = () => {
	return (
		<QueryClientProvider client={queryClient}>
			<RouterProvider router={router} />
		</QueryClientProvider>
	);
};

export default App;
