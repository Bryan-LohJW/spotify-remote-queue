import { useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { SubmitHandler, useForm } from 'react-hook-form';
import { IoShareOutline } from 'react-icons/io5';
import Search from '../../components/search/Search';
import { RoomInformation } from '../home/Home';
import { saveInformation } from '../../store/slice/roomInformationSlice';
import { authenticate, saveJwt } from '../../store/slice/authenticationSlice';
import type { RootState } from '../../store/store';

type Inputs = {
	roomId: string;
	pin: string;
	userId: string;
};

const Room = () => {
	const { roomId } = useParams();
	const dispatch = useDispatch();
	const { register, handleSubmit } = useForm<Inputs>();
	const { isAuthenticated } = useSelector(
		(state: RootState) => state.authentication
	);
	const roomInformation = useSelector(
		(state: RootState) => state.roomInformation
	);

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

		dispatch(
			saveJwt(response.headers.get('Authorization')?.slice(7) || '')
		);
		dispatch(authenticate());
		dispatch(saveInformation(body));
	};

	let display = null;
	if (!isAuthenticated) {
		display = (
			<div>
				<form
					className="mx-auto flex w-1/3 flex-col"
					onSubmit={handleSubmit(onSubmit)}
				>
					<div>
						<label className="text-white">Room Id</label>
						<input
							{...register('roomId')}
							disabled
							value={roomId}
						/>
					</div>
					<div>
						<label className="text-white">Pin</label>
						<input {...register('pin')} />
					</div>
					<div>
						<label className="text-white">Name</label>
						<input {...register('userId')} />
					</div>
					<button
						className="items-center justify-center rounded-md bg-green-500 text-white"
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
						}}
					></IoShareOutline>
					<input
						className="hidden text-black"
						id="link"
						type="textarea"
						defaultValue={
							import.meta.env.VITE_BASE_URI +
							`/room/${roomInformation.roomId}`
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
