import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useForm, SubmitHandler } from 'react-hook-form';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { BsSpotify } from 'react-icons/bs';
import { saveInformation } from '../../store/slice/roomInformationSlice';
import { authenticate, saveJwt } from '../../store/slice/authenticationSlice';

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
	const navigate = useNavigate();
	const dispatch = useDispatch();
	const [searchParams] = useSearchParams();

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
				return;
			}

			const body = (await response.json()) as RoomInformation;
			const authorizationHeader = response.headers.get('Authorization');
			authorizationHeader &&
				dispatch(saveJwt(authorizationHeader.slice(7)));
			dispatch(authenticate());
			dispatch(saveInformation(body));
			navigate(`/room/${body.roomId}`);
		};

		const code = searchParams.get('code');
		if (code !== null) {
			registerRoom(code);
		}
	}, [searchParams, dispatch, navigate]);

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
		</div>
	);
};

export default Home;
