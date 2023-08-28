export type SearchResponse = {
	tracks: Tracks;
	artists: Artists;
	albums: Albums;
	playlists: Playlists;
	shows: Shows;
	episodes: Episodes;
	audiobooks: Audiobooks;
};

type Tracks = {
	href: string;
	limit: number;
	next: string;
	offset: number;
	previous: string;
	total: number;
	items: TracksTrack[];
};

export type TracksTrack = {
	album: TracksTrackAlbum;
	artists: TracksTrackArtists[];
	available_markets: string[];
	disc_number: number;
	duration_ms: number;
	explicit: boolean;
	external_ids: ExternalIds;
	external_urls: ExternalUrls;
	href: string;
	id: string;
	is_playable: boolean;
	linked_from: object;
	restrictions: Restrictions;
	name: string;
	popularity: number;
	preview_url: string;
	track_number: number;
	type: string;
	uri: string;
	is_local: boolean;
};

type TracksTrackAlbum = {
	album_type: string;
	total_tracks: number;
	available_markets: string[];
	external_urls: ExternalUrls;
	href: string;
	id: string;
	images: Image[];
	name: string;
	release_date: string;
	restrictions: Restrictions;
	type: string;
	copyrights: Copyrights[];
	external_ids: ExternalIds;
	genres: string[];
	label: string;
	popularity: number;
	album_group: string;
	artists: TracksTrackAlbumArtist[];
};

type TracksTrackAlbumArtist = {
	external_urls: ExternalUrls;
	href: string;
	id: string;
	name: string;
	type: string;
	uri: string;
};

type TracksTrackArtists = {
	external_urls: ExternalUrls;
	followers: Followers;
	genres: string[];
	href: string;
	id: string;
	images: Image[];
	name: string;
	popularity: number;
	type: string;
	uri: string;
};

type Artists = {
	href: string;
	limit: number;
	next: string;
	offset: number;
	previous: string;
	total: number;
	items: ArtistsArtist[];
};

type ArtistsArtist = {
	external_urls: ExternalUrls;
	followers: Followers;
	genres: string[];
	href: string;
	id: string;
	images: Image[];
	name: string;
	popularity: number;
	type: string;
	uri: string;
};

type Albums = {
	href: string;
	limit: number;
	next: string;
	offset: number;
	previous: string;
	total: number;
	items: AlbumsAlbum[];
};

type AlbumsAlbum = {
	album_type: string;
	total_tracks: number;
	available_markets: string[];
	external_urls: ExternalUrls;
	href: string;
	id: string;
	images: Image[];
	name: string;
	release_date: string;
	release_date_precision: string;
	restrictions: Restrictions;
	type: string;
	uri: string;
	copyrights: Copyrights;
	external_ids: ExternalIds;
	genres: string[];
	label: string;
	popularity: number;
	album_group: string;
	artists: AlbumsAlbumArtist[];
};

type AlbumsAlbumArtist = {
	external_urls: ExternalUrls;
	href: string;
	id: string;
	name: string;
	type: string;
	uri: string;
};

type Playlists = {
	href: string;
	limit: number;
	next: string;
	offset: number;
	previous: string;
	total: number;
	items: PlaylistsPlaylist[];
};

type PlaylistsPlaylist = {
	collaborative: boolean;
	description: string;
	external_urls: ExternalUrls;
	href: string;
	id: string;
	images: Image[];
	name: string;
	owner: {
		external_urls: ExternalUrls;
		followers: Followers;
		href: string;
		id: string;
		type: string;
		uri: string;
		display_name: string;
	};
	public: boolean;
	snapshot_id: string;
	tracks: PlaylistsPlaylistTracks;
	type: string;
	uri: string;
};

type PlaylistsPlaylistTracks = {
	href: string;
	total: number;
};

type Shows = {
	href: string;
	limit: number;
	next: string;
	offset: number;
	previous: string;
	total: number;
	items: ShowsShow[];
};

type ShowsShow = {
	available_markets: string[];
	copyrights: Copyrights;
	description: string;
	html_description: string;
	explicit: boolean;
	external_urls: ExternalUrls;
	href: string;
	id: string;
	images: Image[];
	is_externally_hosted: boolean;
	languages: string[];
	media_type: string;
	name: string;
	publisher: string;
	type: string;
	uri: string;
	total_episodes: number;
};

type Episodes = {
	href: string;
	limit: number;
	next: string;
	offset: number;
	previous: string;
	total: number;
	items: EpisodesEpisode[];
};

type EpisodesEpisode = {
	audio_preview_url: string;
	description: string;
	html_description: string;
	duration_ms: number;
	explicit: boolean;
	external_urls: ExternalUrls;
	href: string;
	id: string;
	images: Image[];
	is_externally_hosted: string;
	is_playable: string;
	language: string;
	languages: string[];
	name: string;
	release_date: string;
	release_date_precision: string;
	resume_point: {
		fully_played: boolean;
		resume_position_ms: number;
	};
	type: string;
	uri: string;
	restrictions: Restrictions;
};

type Audiobooks = {
	href: string;
	limit: number;
	next: string;
	offset: number;
	previous: string;
	total: number;
	items: AudiobooksAudiobook[];
};

type AudiobooksAudiobook = {
	authors: {
		name: string;
	}[];
	available_markets: string[];
	copyrights: Copyrights;
	description: string;
	html_description: string;
	edition: string;
	explicit: boolean;
	external_urls: ExternalUrls;
	href: string;
	id: string;
	images: Image[];
	languages: string[];
	media_type: string;
	name: string;
	narrators: {
		name: string;
	}[];
	publisher: string;
	type: string;
	uri: string;
	total_chapters: number;
};

type ExternalUrls = {
	spotify: string;
};

type Image = {
	url: string;
	height: number;
	width: number;
};

type Restrictions = {
	reason: string;
};

type Copyrights = {
	text: string;
	type: string;
};

type ExternalIds = {
	isrc: string;
	ean: string;
	upc: string;
};

type Followers = {
	href: string;
	total: number;
};
