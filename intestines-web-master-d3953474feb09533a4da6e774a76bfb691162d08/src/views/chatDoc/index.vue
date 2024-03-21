<script setup>
const scrollbarRef = ref()

const msgList = ref([])
const msg = ref("")

const hotList = ref([])
getList()
async function getList() {
	const { data } = await api.get("/web/chatDocKeyWords/top10")
	hotList.value = data
}
const send = async (val, type) => {
	if (val) {
		msg.value = val
	}
	if (!msg.value.trim()) return
	const searchText = msg.value
	msg.value = ""

	msgList.value.push({
		type: 1,
		msg: searchText,
	})
	const { data } = await api.post(`/web/chatDoc/chat?isKeyWords=${type || 0}&searchText=${searchText}`)

	const msg1 = data || [
		{ keyWords: searchText, chatDocs: [{ answer: `Sorry, I can't give a relevant reply based on your question, you can try a different question or keyword.` }] },
	]

	msgList.value.push({
		type: 2,
		msg: msg1,
	})
	setTimeout(() => {
		scrollbarRef.value.setScrollTop(msgList.value.length * 999)
	})
}
const clear = () => {
	msgList.value = []
}
// 回车不换行 变成发送消息
const carriageReturn = (event) => {
	if (event.ctrlKey) {
		event.returnValue = false
		msg.value = msg.value + "\n"
		return false
	} else send()
}
</script>

<template>
	<div class="content">
		<div class="l">
			<img src="../../assets/images/chatDoc.webp" alt="" srcset="" />
			<div class="tips"><span class="iconfont icon-zhiding"></span>Top 10 key words：</div>
			<div class="list">
				<template v-for="v in hotList" :key="v">
					<div class="item">
						<el-tooltip class="box-item" effect="dark" :content="v.keyWords" placement="right">
							<el-text truncated @click="send(v.keyWords, 1)">{{ v.keyWords }}</el-text>
						</el-tooltip>
					</div>
				</template>
			</div>
		</div>
		<div class="r">
			<el-scrollbar class="list mb20" ref="scrollbarRef">
				<div class="tips" v-if="!msgList.length">
					<div class="text mb20">Try sending me some questions, such as "What are data sources?"</div>
					<el-button @click="send(`What are data sources?`)">
						<span class="iconfont icon-jiazai_shuaxin"></span> Have a go
					</el-button>
				</div>
				<template v-for="(v, index) in msgList" :key="index">
					<div class="item me mb20 pl40" v-if="v.type === 1">
						<div class="text">{{ v.msg }}</div>
						<div class="img ml20">
							<img src="../../assets/images/head.webp" alt="" srcset="" />
						</div>
					</div>
					<div class="item chat mb20 pr40" v-else>
						<div class="img mr20">
							<img src="../../assets/images/chat.webp" alt="" srcset="" />
						</div>
						<div class="con">
							<template v-for="m in v.msg">
								<div class="title">{{ m.keyWords }}</div>
								<template v-for="n in m.chatDocs">
									<div class="text" v-if="n.question">{{ n.question }}</div>
									<div class="text">{{ n.answer }}</div>
								</template>
							</template>
						</div>
					</div>
				</template>
			</el-scrollbar>
			<div class="footer">
				<el-input
					v-model="msg"
					type="textarea"
					:autosize="{ maxRows: 4 }"
					resize="none"
					@keydown.prevent.enter="carriageReturn($event)"
					size="large"
					placeholder="Enter content to start the chat (Ctel+Enter line wrap)"
				/>
				<div class="send" @click="send()">
					<span class="iconfont icon-xiaoxi"></span>
					<span>Send</span>
				</div>
				<div class="send" @click="clear()">
					<span class="iconfont icon-shanchu"></span>
					<span>clear</span>
				</div>
			</div>
		</div>
	</div>
</template>

<style lang="scss" scoped>
@import url(./index.scss);
</style>
