<script setup>
import dayjs from "dayjs"

dayjs.locale("en")

const route = useRoute()
const { id: queryId } = route.query

let isRequest = false

let id = queryId

const hidePre = ref(false)
const hideNext = ref(false)

const d = ref({})
async function getData() {
	isRequest = true

	const { data } = await api.get("/web/news/newsDetail", { id })
	d.value = data

	isRequest = false
}
getData()

const previous = async () => {
	if (isRequest) return

	const { data } = await api.get("/web/news/previousNews", { id })
	if (!data) {
		hidePre.value = true
		return
	}
	hideNext.value = false
	d.value = data

	id = data.id
	console.log(id)

	isRequest = false
}
const next = async () => {
	if (isRequest) return

	const { data } = await api.get("/web/news/nextNews", { id })
	if (!data) {
		hideNext.value = true
		return
	}
	hidePre.value = false
	d.value = data

	id = data.id

	isRequest = false
}
</script>

<template>
	<div class="wrap">
		<LayoutNav />
		<div class="header">
			<img class="home_icon" src="@/assets/images/homeIcon.webp" alt="home icon" />

			<el-breadcrumb separator="|">
				<el-breadcrumb-item>Home</el-breadcrumb-item>
				<el-breadcrumb-item>News</el-breadcrumb-item>
				<el-breadcrumb-item>Article</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="content">
		<Title title="Article" />
			<div class="title">{{ d.title }}</div>
			<div class="date">{{ d.published }} | {{ dayjs(d.createTime).format("DD MMMM YYYY") }}</div>
			<div class="separator_line mt30 mb30"></div>
			<div class="text_content mb40">
				<div v-html="d.content" style="position: relative"></div>
			</div>
			<div v-if="d.detailsImg" class="image_wrap mb30">
				<img :src="picUrl(d.detailsImg)" alt="" />
			</div>
			<div class="separator_line mb30"></div>
			<div class="bottom_tool select_none">
				<div class="left" @click="previous"><span v-show="!hidePre">&lt;　Previous</span></div>
				<div class="right" @click="next"><span v-show="!hideNext">Next　&gt;</span></div>
			</div>
		</div>
		<LayoutFooter />
	</div>
</template>

<style lang="scss" scoped>
.header {
	display: flex;
	align-items: center;
	padding: 40px 40px 30px;
	.home_icon {
		margin-right: 12px;
	}
}
.content {
	display: flex;
	flex-direction: column;
	.title {
		font-weight: 700;
		font-size: $fontSize30;
		margin-bottom: 40px;
	}
	.date {
		color: #999999;
	}
	.bottom_tool {
		color: $themeColor;
		display: flex;
		justify-content: space-between;
		.left,
		.right {
			cursor: pointer;
		}
	}
}
</style>
