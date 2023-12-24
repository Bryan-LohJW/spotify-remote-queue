/** @type {import('tailwindcss').Config} */
export default {
	content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
	theme: {
		colors: {
			green: '#1DB954',
			black: '#121212',
			lightblack: '#212121',
			grey: '#535353',
			lightgrey: '#B3B3B3',
			white: 'white',
		},
		fontFamily: {
			base: ['Arial', 'sans-serif'],
		},
	},
	plugins: [],
};
