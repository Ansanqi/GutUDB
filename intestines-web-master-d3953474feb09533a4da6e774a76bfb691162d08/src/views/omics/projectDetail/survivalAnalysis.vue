<script setup>

const props = defineProps({
	survivalAnalysis: String,
})
</script>

<template>
	<div class="my-table">
		<el-image :src="picUrl(props?.survivalAnalysis)" v-if="props?.survivalAnalysis">
			<template #error>
				<div class="image-slot">
					<el-empty description="No Data" />
				</div>
			</template>
		</el-image>
		<el-empty v-else />
	</div>
</template>

<style lang="scss" scoped>

</style>
