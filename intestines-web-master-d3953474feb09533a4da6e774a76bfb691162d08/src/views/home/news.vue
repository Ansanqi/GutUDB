<script setup>
import dayjs from "dayjs"

const router = useRouter()

dayjs.locale("en")

// 分页
const pagination = reactive({
	current: 1,
	size: 10,
	total: 0,
})

const dataArr = ref([])

getList()
async function getList() {
	const {
		data: { records, total },
	} = await api.get("/web/news/pageList")
	dataArr.value = records
	pagination.total = parseInt(total)
}

const goDetail = (id) => {
	router.push({ path: "/home/news/article", query: { id } })
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
			</el-breadcrumb>
		</div>
		<div class="content">
		<Title title="News" />
			<div v-for="item in dataArr" class="card_wrap" @click="goDetail(item.id)">
				<div class="left">{{ item.published }} | {{ dayjs(item.createTime).format("DD MMMM YYYY") }}</div>
				<div class="right">
					<div class="top">{{ item.title }}</div>
					<div class="article_content">
						{{ item.content }}
					</div>
				</div>
			</div>
			<div class="pages">
				<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()"></Pagination>
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
	.card_wrap {
		cursor: pointer;
		padding: 20px 0;
		border-bottom: 1px solid $separatorColor;
		display: flex;
		.left {
			width: 20%;
		}
		.right {
			flex: 1;
			display: flex;
			flex-direction: column;
			.top {
				font-weight: 700;
				font-size: $fontSize18;
				margin-bottom: 30px;
			}
		}
	}
	.pages {
		display: flex;
		justify-content: end;
		margin-top: 40px;
	}
}
</style>
