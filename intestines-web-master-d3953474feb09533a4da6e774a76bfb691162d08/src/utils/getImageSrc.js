export const getSrc = (url) => {
	return new URL(`../assets/images/${url}`, import.meta.url).href
}

export const picUrl = (url) => {
	if (url && url.length > 0) {
		let imgUrl = window.config.imgUrl
		// if (import.meta.env.DEV) {
		// 	console.log(window.config.imgUrl + '/' + url);
		// 	imgUrl = '/file'
		// } else {
		// 	imgUrl = window.config.imgUrl
		// }
		if (url.indexOf("/") == 0) {
			return imgUrl + url
		} else {
			return imgUrl + "/" + url
		}
	}
	return null
}
