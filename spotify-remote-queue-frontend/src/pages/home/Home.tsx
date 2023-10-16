import { useEffect, useState } from 'react';
import { useCookies } from 'react-cookie';
import { useForm, SubmitHandler } from 'react-hook-form';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { BsSpotify } from 'react-icons/bs';
import toast from 'react-hot-toast';

type Inputs = {
	roomId: string;
};

export type RoomInformation = {
	roomId: string;
	pin: string;
	expiry: string;
	active: boolean;
};

const Home = () => {
	const { register, handleSubmit } = useForm<Inputs>();
	const [prevRoom, setPrevRoom] = useState(false);
	const navigate = useNavigate();
	const [searchParams] = useSearchParams();
	const [cookie, setCookie, removeCookie] = useCookies([
		'roomId',
		'roomPin',
		'roomExpiry',
		'jwtToken',
		'jwtExpiry',
	]);
	let code = searchParams.get('code');

	const onSubmit: SubmitHandler<Inputs> = (data) => {
		navigate(`/room/${data.roomId.trim()}`);
	};

	const inputState = 'HARDCODE';
	const spotifyOauth = `https://accounts.spotify.com/authorize
	?client_id=${import.meta.env.VITE_SPOTIFY_CLIENT_ID}
	&redirect_uri=${import.meta.env.VITE_BASE_URI}
	&response_type=${import.meta.env.VITE_SPOTIFY_RESPONSE_TYPE}
	&scope=${import.meta.env.VITE_SPOTIFY_AUTHORITY_SCOPE}
	&state=${inputState}
	&show_dialog=true`;

	useEffect(() => {
		const registerRoom = async (code: string) => {
			const response = await fetch(
				import.meta.env.VITE_BACKEND_ENDPOINT_BASE +
					'/api/v1/spotify/register/room',
				{
					method: 'POST',
					headers: {
						'Access-Control-Allow-Credentials': 'true',
						credentials: 'include',
						'x-api-key': import.meta.env.VITE_BACKEND_API_KEY,
						'Content-Type': 'application/json',
					},
					body: JSON.stringify({ code }),
				}
			);

			if (!response.ok) {
				removeCookie('roomId');
				removeCookie('roomPin');
				removeCookie('roomExpiry');
				removeCookie('jwtExpiry');
				removeCookie('jwtToken');
				toast.error('Authentication Error', {
					position: 'bottom-center',
				});
				return;
			}

			const body = (await response.json()) as RoomInformation;
			const authorizationHeader = response.headers.get('Authorization');

			if (authorizationHeader) {
				setCookie('jwtToken', authorizationHeader.slice(7), {
					maxAge: 3600,
				});
				setCookie('jwtExpiry', Date.now() + 3600000 + '', {
					maxAge: 3600,
				});
			}
			setCookie('roomId', body.roomId, { maxAge: 3600 });
			setCookie('roomExpiry', Date.parse(body.expiry), { maxAge: 3600 });
			setCookie('roomPin', body.pin, { maxAge: 3600 });

			navigate(`/room/${body.roomId}`);
		};

		if (cookie.roomId) {
			if (
				parseInt(cookie.jwtExpiry, 10) < Date.now() &&
				parseInt(cookie.roomExpiry, 10) < Date.now()
			) {
				removeCookie('roomId');
				removeCookie('roomPin');
				removeCookie('roomExpiry');
				removeCookie('jwtExpiry');
				removeCookie('jwtToken');
			} else {
				setPrevRoom(true);
			}
		}

		if (code == null) return;
		registerRoom(code).catch(() => {
			removeCookie('roomId');
			removeCookie('roomPin');
			removeCookie('roomExpiry');
			removeCookie('jwtExpiry');
			removeCookie('jwtToken');
			toast.error('Server Error', {
				position: 'bottom-center',
			});
		});
		code = null;
	}, [searchParams, navigate, cookie, setCookie, removeCookie, code]);

	return (
		<div>
			<p className="mx-auto mt-20 w-fit text-center text-3xl text-white md:flex md:items-center md:gap-4">
				Remote Queue For
				<img
					src={'/assets/images/spotify-logo.png'}
					className="mx-auto my-5 w-52"
				/>
			</p>
			<div className="my-20 flex flex-col gap-2">
				<a
					className="mx-auto flex h-14 w-64 items-center justify-center gap-2 rounded-md bg-green-500 py-3 text-white"
					href={spotifyOauth}
				>
					<BsSpotify className="h-6 w-6"></BsSpotify>
					Log in with Spotify
				</a>
				<p className="mx-auto w-fit text-2xl text-white">OR</p>
				<form
					onSubmit={handleSubmit(onSubmit)}
					className="mx-auto flex w-64 flex-col gap-4"
				>
					<input
						type="text"
						placeholder="Room Number"
						className="rounded-md p-1"
						{...register('roomId', { required: true })}
					/>
					<button
						type="submit"
						className="mx-auto h-10 w-32 rounded-md bg-green-500 text-center text-white"
					>
						Enter
					</button>
				</form>
			</div>
			{prevRoom && (
				<a
					className="mx-auto flex h-10 w-32 items-center justify-center gap-2 rounded-md bg-green-500 py-3 text-white"
					onClick={() => {
						const roomId = cookie.roomId;
						navigate(`/room/${roomId}`);
					}}
				>
					Previous Room
				</a>
			)}
		</div>
	);
};

export default Home;
