import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import Home from './pages/home/Home';
import SpotifyResponse from './pages/spotify-response/SpotifyResponse';
import { QueryClient, QueryClientProvider } from 'react-query';
import Room from './pages/room/Room';
import { Provider } from 'react-redux';
import { store } from './store/store';
import { CookiesProvider } from 'react-cookie';

const router = createBrowserRouter([
	{
		element: <Home />,
		path: '/',
	},
	{
		element: <SpotifyResponse />,
		path: '/spotify-response',
	},
	{
		element: <Room></Room>,
		path: '/room/:roomId',
	},
]);

const queryClient = new QueryClient();

const App = () => {
	return (
		<Provider store={store}>
			<QueryClientProvider client={queryClient}>
				<CookiesProvider>
					<RouterProvider router={router} />
				</CookiesProvider>
			</QueryClientProvider>
		</Provider>
	);
};

export default App;
