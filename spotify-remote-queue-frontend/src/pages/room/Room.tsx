import { useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { SubmitHandler, useForm } from 'react-hook-form';
import { IoShareOutline } from 'react-icons/io5';
import { RoomInformation } from '../home/Home';
import Search from '../../components/search/Search';
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
	const { isAuthenticated, userId } = useSelector(
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
			<div>
				<div className="flex flex-col items-center text-white">
					<p className="text-lg font-semibold">Welcome {userId}</p>
					<div className="flex gap-2">
						<p>Share the session</p>
						<IoShareOutline
							className="h-6 w-6"
							onClick={() => {
								navigator.clipboard.writeText(
									import.meta.env.VITE_BASE_URI +
										`/room/${roomInformation.roomId}`
								);
							}}
						></IoShareOutline>
					</div>
					<p>Pin: {roomInformation.pin}</p>
				</div>
				<div className="h-20"></div>
				<Search></Search>
			</div>
		);
	}
	return display;
};

export default Room;
