import { useParams, useSearchParams } from 'react-router-dom';
import { SubmitHandler, useForm } from 'react-hook-form';
import { IoShareOutline } from 'react-icons/io5';
import Search from '../../components/search/Search';
import { RoomInformation } from '../home/Home';
import { useEffect, useState } from 'react';
import { useCookies } from 'react-cookie';
import toast from 'react-hot-toast';

type Inputs = {
	roomId: string;
	pin: string;
	userId: string;
};

const Room = () => {
	const { roomId } = useParams();
	const [URLSearchParams] = useSearchParams();
	const { register, handleSubmit } = useForm<Inputs>();
	const [isAuthenticated, setIsAuthenticated] = useState(false);
	const [cookie, setCookie] = useCookies([
		'roomId',
		'roomPin',
		'roomExpiry',
		'jwtToken',
		'jwtExpiry',
	]);

	useEffect(() => {
		if (roomId == cookie.roomId) {
			if (parseInt(cookie.jwtExpiry) > Date.now()) {
				setIsAuthenticated(true);
			}
		}
	}, [cookie, roomId]);

	const onSubmit: SubmitHandler<Inputs> = async (data) => {
		const url =
			import.meta.env.VITE_BACKEND_ENDPOINT_BASE +
			'/api/v1/spotify/register/user';
		const response = await fetch(url, {
			method: 'POST',
			headers: {
				'x-api-key': import.meta.env.VITE_BACKEND_API_KEY,
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({
				userId: data.userId,
				pin: data.pin,
				roomId: roomId,
			}),
		});
		if (!response.ok) {
			return;
		}
		const body = (await response.json()) as RoomInformation;
		body.roomId = roomId || '';
		body.pin = data.pin;

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
		setIsAuthenticated(true);
	};

	let display = null;
	if (!isAuthenticated) {
		display = (
			<div>
				<form
					className="mx-auto flex w-2/3 flex-col gap-2 md:w-72"
					onSubmit={handleSubmit(onSubmit)}
				>
					<div className="flex justify-between">
						<label className="text-white">Room Id</label>
						<input
							className="w-2/3 rounded-md pl-3"
							{...register('roomId')}
							disabled
							value={roomId}
						/>
					</div>
					<div className="flex justify-between">
						<label className="text-white">Pin</label>
						<input
							className="w-2/3 rounded-md pl-3"
							{...register('pin')}
							value={URLSearchParams.get('pin') || ''}
						/>
					</div>
					<div className="flex justify-between">
						<label className="text-white">Name</label>
						<input
							className="w-2/3 rounded-md pl-3"
							{...register('userId')}
						/>
					</div>
					<button
						className="mx-auto h-8 w-1/2 items-center justify-center rounded-md bg-green-500 text-lg font-semibold text-white"
						type="submit"
					>
						Enter
					</button>
				</form>
			</div>
		);
	}
	if (isAuthenticated) {
		display = (
			<div className="">
				<div className="mb-10 flex items-center justify-center bg-gray-700 py-5 text-white">
					<p className="text-lg font-semibold">
						Remote Queue for Spotify
					</p>
					<IoShareOutline
						className="fixed right-3 h-8 w-8 md:relative md:left-10"
						onClick={() => {
							const linkElement = document.getElementById(
								'link'
							) as HTMLInputElement;

							if (linkElement == null) {
								console.error('link element not found');
								return;
							}
							linkElement.focus();
							linkElement.select();
							linkElement.setSelectionRange(0, 99999);
							navigator.clipboard.writeText(linkElement.value);
							toast.success('Copied to clipboard', {
								position: 'top-center',
							});
						}}
					></IoShareOutline>
					<input
						className="hidden text-black"
						id="link"
						type="textarea"
						defaultValue={
							import.meta.env.VITE_BASE_URI +
							`/room/${cookie.roomId}?pin=${cookie.roomPin}`
						}
					/>
				</div>
				<Search></Search>
			</div>
		);
	}
	return display;
};

export default Room;
