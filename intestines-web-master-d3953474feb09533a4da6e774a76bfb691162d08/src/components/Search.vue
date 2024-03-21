<script setup>
const route = useRoute()
const currName = route.matched.slice(-1)[0].name
const currSearchObj = config.serchObj[props.title || currName]

const props = defineProps({
	search: {
		type: Object,
		required: true,
		default: {},
	},
	fn: {
		type: Function,
		required: true,
	},
	title: {
		type: String,
	},
	down: {
		type: Function,
		required: false,
	},
	downPage: {
		type: Number,
		required: false,
	},
})

const dialogVisible = ref(false)
const selectNumber = ref(0)
const downArr = ref([])

function splitNumberToArray(number, interval) {
	const result = []
	let start = 1

	let i = 1
	while (start <= number) {
		let end = start + interval - 1
		if (end > number) {
			end = number
		}

		result.push({
			s: start,
			e: end,
			n: end - start + 1,
			p: i,
		})
		start = end + 1
		i++
	}

	return result
}

const splitNumber = 100000

const showDown = () => {
	if (props.downPage) {
		const total = props.downPage
		if (total > splitNumber) {
			downArr.value = splitNumberToArray(total, splitNumber)
		} else {
			downArr.value = [
				{
					s: 1,
					e: total,
					n: total - 1,
					p: 1,
				},
			]
		}

		dialogVisible.value = true
	} else {
		console.error("未传递downPage参数")
		props.down()
	}
}
const down = () => {
	const obj = downArr.value[selectNumber.value]
	props.down(obj.p, obj.n)
}
const localeString = (number) => number.toLocaleString()
</script>

<template>
	<div class="my-search">
		<div class="search">
			<template v-for="v in currSearchObj" :key="v.name">
				<template v-if="v.type === 'input'">
					<el-input v-model="props.search[v.name]" :placeholder="v.placeholder" clearable size="large" class="mb20" @keyup.enter="props.fn()" @clear="props.fn()">
						<template #append>
							<span class="iconfont icon-jiyin" @click="props.fn()"></span>
						</template>
					</el-input>
				</template>
				<template v-else-if="v.type === 'select'">
					<el-select filterable v-model="props.search[v.name]" @change="props.fn()" :placeholder="v.placeholder" class="mr20 mb20" clearable size="large">
						<el-option v-for="m in v.list" :label="m" :value="m" />
					</el-select>
				</template>
			</template>
		</div>
		<slot></slot>
		<div class="my-tsv" @click="showDown()" v-if="props.down">
			<span class="iconfont icon-xiazai"></span>
			<span>CSV</span>
		</div>
	</div>
	<el-dialog v-model="dialogVisible" width="300px" :center="true">
		<el-radio-group v-model="selectNumber" class="df fdc">
			<el-radio v-for="(v, i) in downArr" :label="i" size="large">Rows {{ localeString(v.s) }} - {{ localeString(v.e) }}</el-radio>
		</el-radio-group>
		<template #footer>
			<span class="dialog-footer">
				<el-button type="primary" @click="down"> Download </el-button>
			</span>
		</template>
	</el-dialog>
</template>

<style scoped lang="scss">
.el-radio-group {
	align-items: flex-start;
}
@import url("@/styles/content.scss");
</style>
