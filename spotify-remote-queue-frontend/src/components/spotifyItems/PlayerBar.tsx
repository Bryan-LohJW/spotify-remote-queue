import { useCookies } from 'react-cookie';
import { BiSkipNext } from 'react-icons/bi';
import { BsFillPlayFill, BsFillPauseFill } from 'react-icons/bs';
import cookieList from '../../constants/CookieList';
import toast from 'react-hot-toast';
import { ErrorMessage } from '../../types/ErrorResponse';

const PlayerBar = () => {
	const [cookie] = useCookies(cookieList);

	const accessToken = 'Bearer ' + cookie.jwtToken;
	const play = async () => {
		const url =
			import.meta.env.VITE_BACKEND_ENDPOINT_BASE +
			'/api/v1/spotify/player/play';

		const response = await fetch(url, {
			method: 'PUT',
			headers: {
				'x-api-key': import.meta.env.VITE_BACKEND_API_KEY,
				'Content-Type': 'application/json',
				Authorization: accessToken,
			},
		});
		if (!response.ok) {
			const body = (await response.json()) as ErrorMessage;
			toast.error(body.message);
		}
		toast.success('Success');
	};

	const pause = async () => {
		const url =
			import.meta.env.VITE_BACKEND_ENDPOINT_BASE +
			'/api/v1/spotify/player/pause';

		const response = await fetch(url, {
			method: 'PUT',
			headers: {
				'x-api-key': import.meta.env.VITE_BACKEND_API_KEY,
				'Content-Type': 'application/json',
				Authorization: accessToken,
			},
		});
		if (!response.ok) {
			const body = (await response.json()) as ErrorMessage;
			toast.error(body.message);
		}
		toast.success('Success');
	};

	const next = async () => {
		const url =
			import.meta.env.VITE_BACKEND_ENDPOINT_BASE +
			'/api/v1/spotify/player/next';

		const response = await fetch(url, {
			method: 'POST',
			headers: {
				'x-api-key': import.meta.env.VITE_BACKEND_API_KEY,
				'Content-Type': 'application/json',
				Authorization: accessToken,
			},
		});
		if (!response.ok) {
			const body = (await response.json()) as ErrorMessage;
			toast.error(body.message);
		}
		toast.success('Success');
	};

	return (
		<div className="h-20">
			<div className="fixed bottom-0 flex h-20 w-full bg-gray-500">
				<div className="mx-auto flex w-44 justify-between">
					<button
						onClick={() => {
							play();
						}}
					>
						<BsFillPlayFill className="h-10 w-10"></BsFillPlayFill>
					</button>
					<button
						onClick={() => {
							pause();
						}}
					>
						<BsFillPauseFill className="h-10 w-10"></BsFillPauseFill>
					</button>
					<button
						onClick={() => {
							next();
						}}
					>
						<BiSkipNext className="h-10 w-10"></BiSkipNext>
					</button>
				</div>
			</div>
		</div>
	);
};

export default PlayerBar;
