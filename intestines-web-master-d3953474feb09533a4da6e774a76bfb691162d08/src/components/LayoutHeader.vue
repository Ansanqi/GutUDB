<script setup>
const route = useRoute()
const routeHierarchy = computed(() => {
	const matchedRoutes = route.matched
	const arr = []
	let firstName = ""
	for (let i = 0, len = matchedRoutes.length; i < len; i++) {
		const route = matchedRoutes[i]
		if (i == 0 && !route.name) {
			firstName = route.children[0].name
		} else if (i == 1 && firstName == route.name) {
			continue
		}

		arr.push({
			path: route.path,
			name: route.name ? route.name : route.children[0].name,
		})
	}
	return arr
})
</script>

<template>
	<div class="header">
		<!-- <img class="home_icon" src="@/assets/images/homeIcon.webp" alt="home icon" /> -->

		<el-breadcrumb separator="/">
			<!-- <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
			<el-breadcrumb-item v-for="(route, index) in routeHierarchy" :key="index" :to="route.path">{{ route.name }}</el-breadcrumb-item> -->
			<!-- <el-breadcrumb-item>Home</el-breadcrumb-item> -->
			<el-breadcrumb-item v-for="(route, index) in routeHierarchy" :key="index">{{ route.name }}</el-breadcrumb-item>
		</el-breadcrumb>
	</div>
</template>

<style scoped lang="scss">
.header {
	font-family: "AlibabaPuHuiTi-Medium";
	font-size: $fontSize16;
	height: 72px;
	display: flex;
	align-items: center;
	margin: 25px 40px;
	padding-left: 30px;
	background: url(/src/assets/images/bg1.webp) center no-repeat;
	border-radius: 4px;
	background-size: cover;
	::v-deep(.el-breadcrumb) {
		display: flex;
		.el-breadcrumb__inner,
		.el-breadcrumb__separator {
			color: #fff;
		}
		.el-breadcrumb__item {
			&:first-child {
				.el-breadcrumb__inner {
					font-size: $fontSize28;
					// color: $tipsColor;
				}
			}
		}
	}
	.home_icon {
		margin-right: 12px;
	}
}
</style>
