<script setup>
const route = useRoute()

const currentName = ref("")

const isHome = ref(route.name == "Home")

if (route.matched[0]?.children?.length) {
	currentName.value = route.matched[0].children[0].name
} else {
	currentName.value = route.matched[0].name
}

onBeforeRouteLeave((to, from) => {
	if (to.matched[0]?.children?.length) {
		const name = to.matched[0].children[0].name
		currentName.value = name
	} else {
		const name = to.matched[0].name
		currentName.value = name
	}
})
</script>

<template>
	<div class="nav">
		<div class="left">
			<img v-if="isHome" style="vertical-align: middle; cursor: pointer; height: 40px" src="@/assets/images/home_logo.webp" @click="$router.push('/')" />
			<img v-else style="vertical-align: middle; cursor: pointer; height: 40px" src="@/assets/images/home_logo2.webp" @click="$router.push('/')" />
		</div>
		<div class="right">
			<div :class="{ is_select1: currentName == 'Home',link_d:isHome }" class="link" @click="$router.push('/')"><span class="iconfont icon-shouye-zhihui"></span>Home</div>
			<el-dropdown @command="$router.push(`/omics/${$event}`)">
				<div :class="{ is_select2: currentName == 'Omics',link_d:isHome }" class="link link_omics"><span class="iconfont icon-omics"></span>Omics</div>
				<template #dropdown>
					<el-dropdown-menu>
						<el-dropdown-item command="epigen-omics/dNA-methylation">Epigenomics</el-dropdown-item>
						<el-dropdown-item command="genomics/genomic-alteration">Genomics</el-dropdown-item>
						<el-dropdown-item command="transcriptomics/alternative-splicing">Transcriptomics</el-dropdown-item>
						<el-dropdown-item command="spatial-omics">Spatial Omics</el-dropdown-item>
						<el-dropdown-item command="single-cell-omics/gene-expression">Singel Cell Omics</el-dropdown-item>
						<el-dropdown-item command="prote-omics">Proteomics</el-dropdown-item>
						<el-dropdown-item command="microbi-omics/associated-genera">Microbiomics</el-dropdown-item>
						<el-dropdown-item command="metabol-omics">Metabolomics</el-dropdown-item>
					</el-dropdown-menu>
				</template>
			</el-dropdown>
			<div :class="{ is_select2: currentName == 'ChatDoc',link_d:isHome }" class="link" @click="$router.push('/chat-doc')"><span class="iconfont icon-zhineng"></span>ChatDoc</div>
			<div :class="{ is_select2: currentName == 'Statistics',link_d:isHome }" class="link" @click="$router.push('/statistics')"><span class="iconfont icon-tongjifenxi"></span>Statistics</div>
			<div :class="{ is_select2: currentName == 'FAQ',link_d:isHome }" class="link" @click="$router.push('/faq')"><span class="iconfont icon-zhishiwenda"></span>FAQ</div>
			<div :class="{ is_select2: currentName == 'Contact US',link_d:isHome }" class="link" @click="$router.push('/contact')"><span class="iconfont icon-youxiang"></span>Contact</div>
		</div>
	</div>
</template>

<style scoped lang="scss">
.nav {
	font-family: "AlibabaPuHuiTi-Medium";
	width: 100%;
	height: 80px;
	padding: 0px 40px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	.right {
		display: flex;
		.link {
			padding: 5px;
			cursor: pointer;
			margin-left: 40px;
			color: $fontColor1;
			font-size: $fontSize16;
			span {
				font-size: $fontSize22;
				margin-right: 5px;
			}
		}
		.link:hover {
			color: $tipsColor;
		}
		.is_select1,
		.is_select2 {
			color: $tipsColor;
		}
		.link_d{
			color: white;
		}
		.link_omics {
			display: flex;
			align-items: center;
			// color: white;
		}
		.link_omics:focus-visible {
			outline: none;
		}
	}
}

.is_select1,
.is_select2 {
	position: relative;
}
.is_select1::after,
.is_select2::after {
	content: "";
	position: absolute;
	bottom: -5px; /* 控制下划线的位置，根据实际情况调整 */
	left: 65%;
	// width: 50px;
	width: 50%;
	transform: translateX(-50%);
	height: 2px;
	border-radius: 2px;
}
.is_select1::after {
	background: #ffd24a;
}
.is_select2::after {
	background: $tipsColor;
}

:deep(.el-dropdown-menu__item:not(.is-disabled):focus) {
	background-color: rgba(4, 147, 174, 0.05);
	color: $tipsColor;
}
</style>
