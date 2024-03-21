<script setup>
const props = defineProps({
	page: {
		type: Number,
		default: 1,
	},
	size: {
		type: Number,
		default: 10,
	},
	sizes: {
		type: Array,
		default: [10, 20, 30, 50, 100, 200],
	},
	total: {
		type: Number,
		required: true,
	},
})
const pageSize = ref(props.size)
let page = props.page
const emits = defineEmits(["update:page", "pageChange", "update:size"])

const handleCurrentChange = (val) => {
	const count = Math.ceil(props.total / props.size)
	if (isNaN(val)) val = 1
	if (val < 1) val = 1
	else if (val > count) val = count
	page = val
	emits("update:page", val)
	emits("pageChange")
}

const pageSizeChange = (val) => {
	emits("update:size", val)
	emits("pageChange")
}
</script>

<template>
	<div class="pagination">
		<el-pagination
			v-if="props.total > 0"
			background
			layout="prev, pager, next, sizes, slot"
			v-model:page-size="pageSize"
			:total="props.total"
			:current-page="page"
			:page-sizes="props.sizes"
			@update:page-size="pageSizeChange"
			@current-change="handleCurrentChange"
		>
			<div key="1" class="page">
				<div class="el-input">
					<div class="el-input__wrapper">
						<input class="el-input__inner" v-model.lazy.number="page" @change="() => handleCurrentChange(page)" />
					</div>
				</div>
				<el-text>page</el-text>
			</div>
		</el-pagination>
	</div>
</template>

<style lang="scss" scoped>
.pagination {
	margin-top: 20px;
	:deep(.el-pagination) {
		margin-top: 30px;
		justify-content: end;
		&.is-background {
			.btn-prev,
			.btn-next,
			.el-pager li {
				margin: 0 15px 0 0;
				border: 1px solid #dfe3e7;
			}
		}
		.page {
			margin-left: var(--el-pagination-item-gap);
			display: flex;
			.el-input {
				width: 50px;
				height: 32px;
				margin-right: 15px;
			}
			.el-text {
				color: #333;
				line-height: 32px;
			}
		}
		.el-pagination__sizes {
			margin-left: 0px;
		}
	}
}
:deep(.el-pagination.is-background .el-pager li.is-active) {
	background-color: $themeColor;
}
:deep(.el-pager li:hover) {
	color: $themeColor;
}
</style>
