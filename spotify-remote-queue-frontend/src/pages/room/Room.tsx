import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import type { RootState } from '../../store/store';
import { useForm } from 'react-hook-form';

type Inputs = {
	roomId: string;
	pin: string;
	username: string;
};

const Room = () => {
	const { roomId } = useParams();
	const isAuthenticated = useSelector(
		(state: RootState) => state.authentication.isAuthenticated
	);
	const onSubmit = () => {};
	const { register, handleSubmit } = useForm<Inputs>();
	let display = null;
	if (!isAuthenticated) {
		display = (
			<div>
				<form onSubmit={handleSubmit(onSubmit)}>
					<input {...register('roomId')} disabled value={roomId} />
					<input {...register('pin')} />
					<input {...register('username')} />
					<button type="submit">Enter</button>
				</form>
			</div>
		);
	}

	return display;
};

export default Room;
