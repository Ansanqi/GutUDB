<script setup>
</script>

<template>
	<div class="wrap">
		<LayoutNav />
		<LayoutHeader />
		<RouterView />
		<LayoutFooter />
	</div>
</template>

<style lang="scss" scoped>
.wrap {
	background-color: $backgroundColor;
}
</style>
