import setting from "@/settings"

let _localStorageSave

function addPrefix (key) {
	if (key.indexOf(`${setting.storagePrefix}_`) === 0) {
		return key
	}
	return `${setting.storagePrefix}_${key}`
}


class Storage {
	constructor() {
		const arr = Object.keys(_localStorageSave)
		this._obj = {}
		for (let i = 0, len = arr.length; i < len; i++) {
			const key = arr[i]
			this._obj[key] = _localStorageSave[key]
		}
	}
	setItem (key, value) {
		if (!key) {
			throw new Error("localStorage setItem: key is null")
		}
		if (!value) {
			console.error(`localStorage setItem: key:${key}, value is null`)
			return
		}

		const v = JSON.stringify(value)
		_localStorageSave.setItem(addPrefix(key), v)

		this._obj[addPrefix(key)] = v
	}
	getItem (key) {
		if (!key) {
			throw new Error("localStorage getItem: key is null")
		}
		let v = this._obj[addPrefix(key)]

		try {
			v = JSON.parse(v)
			// eslint-disable-next-line no-empty
		} catch (error) {
		}
		return v
		// return _localStorageSave.getItem(addPrefix(key))
	}
	removeItem (key) {
		if (!key) {
			throw new Error("localStorage removeItem: key is null")
		}
		delete this._obj[addPrefix(key)]
		_localStorageSave.removeItem(addPrefix(key))
	}
	clear () {
		const perfix = `${setting.storagePrefix}_`
		const arr = Object.keys(_localStorageSave)
		for (let i = 0, len = arr.length; i < len; i++) {
			const item = arr[i]
			if (item.indexOf(perfix) == 0 && !setting?.localStorageWhiteList?.some(c => perfix + c === item)) {
				_localStorageSave.removeItem(item)
				delete this._obj[item]
			}
		}
	}
	toString () {
		return this._obj
	}
	[Symbol.toPrimitive] (hint) {
		if (hint === "number") {
			return NaN
		}
		return "[object Storage]"
	}
}

(function changeLocalStorage () {
	_localStorageSave = window.localStorage

	const s = new Storage()

	let p = new Proxy(s, {
		get: function(target, property, receiver) {
			switch (property) {
				case "setItem":
					return Reflect.get(target, property, receiver)
				case "getItem":
					// return s.getItem(property)
					return Reflect.get(target, property, receiver)
				case "removeItem":
					// return target.removeItem(property)
					return Reflect.get(target, property, receiver)
				case "clear":
					return Reflect.get(target, property, receiver)
				case "toString":
					return Reflect.get(target, property, receiver)
				case "_obj":
					return Reflect.get(target, property, receiver)
				default:
					{
						if (typeof (property) == "symbol") {
							return Reflect.get(target, property, receiver)
						}
						return target.getItem(property)
					}
			}
		},
		set: function(target, property, value, receiver) {
			target.setItem(property, value)
			return true
			// return Reflect.set(target, property, value, receiver);
		},
	})

	Object.defineProperty(window, "localStorage", {
		value: p,
		// configurable: false,
		// enumerable: false,
		writable: false
	})
})()
