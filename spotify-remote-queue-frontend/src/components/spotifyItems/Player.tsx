import { IoShareOutline } from 'react-icons/io5';
import Search from '../../components/search/Search';
import { useCookies } from 'react-cookie';
import toast from 'react-hot-toast';
import cookieList from '../../constants/CookieList';
import PlayerBar from './PlayerBar';

const Player = () => {
	const [cookie] = useCookies(cookieList);

	return (
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
			<PlayerBar></PlayerBar>
		</div>
	);
};

export default Player;
