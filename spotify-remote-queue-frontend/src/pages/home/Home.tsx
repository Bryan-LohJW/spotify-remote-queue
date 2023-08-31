import { BsSpotify } from 'react-icons/bs';
import { useForm, SubmitHandler } from 'react-hook-form';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { saveInformation } from '../../store/slice/roomInformationSlice';
import { authenticate, saveJwt } from '../../store/slice/authenticationSlice';
import spotifyLogoUrl from './../../assets/images/spotify-logo.png';

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
	&redirect_uri=${import.meta.env.VITE_SPOTIFY_REDIRECT_URI}
	&response_type=${import.meta.env.VITE_SPOTIFY_RESPONSE_TYPE}
	&scope=${import.meta.env.VITE_SPOTIFY_AUTHORITY_SCOPE}
	&state=${inputState}
	&show_dialog=true`;

	useEffect(() => {
		const registerRoom = async (code: string) => {
			const response = await fetch(
				'http://localhost:8080/api/v1/spotify/register/room',
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
			<div className="h-20"></div>
			<p className="mx-auto w-3/4 text-center text-3xl text-white">
				Remote Queue For
				<img src={spotifyLogoUrl} className="mx-auto w-1/2" />
			</p>
			<div className="h-20"></div>
			<a
				className="mx-auto flex w-64 items-center justify-center gap-2 rounded-md bg-green-500 py-3 text-white"
				href={spotifyOauth}
			>
				<BsSpotify className="h-6 w-6"></BsSpotify>Log in with Spotify
			</a>
			<p className="mx-auto w-fit text-white">OR</p>
			<form
				onSubmit={handleSubmit(onSubmit)}
				className="mx-auto flex w-64 bg-gray-500"
			>
				<input
					type="text"
					placeholder="Room Session Number"
					{...register('roomId', { required: true })}
				/>
				<button type="submit" className="text-center">
					Enter
				</button>
			</form>
		</div>
	);
};

export default Home;
