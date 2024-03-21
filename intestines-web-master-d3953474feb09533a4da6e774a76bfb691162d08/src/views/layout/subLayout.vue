<script setup>
const router = useRouter()
const route = useRoute()

const routeHierarchy = computed(() => {
	const matchedRoutes = route.matched
	if (matchedRoutes.length == 3) {
		const currentName = matchedRoutes[2].name
		const arr = []
		matchedRoutes[1].children.forEach((route) => {
			if (!route.hidden) {
				arr.push({
					path: route.path,
					name: route.name,
					isSelect: route.name == currentName,
				})
			}
		})
		return arr
	} else return []
})

const go = (path) => {
	router.push(path)
}
</script>

<template>
	<div class="subLayout">
		<div class="button_wrap" v-if="routeHierarchy.length">
			<div v-for="v in routeHierarchy" class="button" :class="{ isSelect: v.isSelect }" @click="go(v.path)">{{ v.name }}</div>
		</div>
		<RouterView />
	</div>
</template>

<style lang="scss" scoped>
.button_wrap {
	margin: 0 40px 20px;
	display: flex;
	gap: 20px;
	.button {
		cursor: pointer;
		color: $themeColor;
		padding: 10px;
		height: 40px;
		border-radius: 4px;
		background-color: rgba(3, 147, 174, 0.05);
	}
	.isSelect {
		background-color: $themeColor;
		color: white;
	}
}
</style>
