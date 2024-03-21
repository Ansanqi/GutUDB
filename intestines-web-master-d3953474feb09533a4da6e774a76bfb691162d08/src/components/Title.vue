<script setup>
const route = useRoute()
const router = useRouter()
const props = defineProps({
	isBack: {
		type: Boolean,
		default: false,
	},
	title: {
		type: String,
		default: ""
	}
})
</script>

<template>
	<div class="my-title">
		{{ props.title || route.matched.slice(-1)[0].name }}
		<span v-if="props.isBack" @click="router.back()" class="iconfont icon-fanhui"></span>
		<!-- <el-button v-if="props.isBack" @click="router.back()">返回</el-button> -->
	</div>
</template>

<style scoped lang="scss">
.my-title {
	padding-bottom: 20px;
	margin-bottom: 30px;
	font-size: $fontSize20;
	font-weight: bold;
	border-bottom: 1px solid #eee;
	display: flex;
	align-items: center;
	&::before {
		content: " ";
		margin-right: 10px;
		display: block;
		height: 20px;
		border-left: 2px solid $themeColor;
	}
	span{
		margin-left: 20px;
		cursor: pointer;
	}
}
</style>
