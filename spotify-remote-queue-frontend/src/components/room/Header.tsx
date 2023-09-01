import { IoShareOutline } from 'react-icons/io5';

type Props = {
	name: string;
	roomId: string;
	pin: string;
};

const Header = (props: Props) => {
	return (
		<div className="flex flex-col items-center text-white">
			<p className="text-lg font-semibold">Welcome {props.name}</p>
			<div className="flex gap-2">
				<p>Share the session</p>
				<IoShareOutline
					className="h-6 w-6"
					onClick={() => {
						navigator.clipboard.writeText(
							import.meta.env.VITE_BASE_URI +
								`/room/${props.roomId}`
						);
					}}
				></IoShareOutline>
			</div>
			<p>Pin: {props.pin}</p>
		</div>
	);
};

export default Header;
