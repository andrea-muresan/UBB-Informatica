import Router from "koa-router";
import { broadcast } from "./websocket.js";
import { relicStore } from "./RelicsStore.js";

export const relicRouter = new Router();

relicRouter.get('/', async (ctx) => {
    const userId = ctx.state.user._id;
    const relics = await relicStore.find({ userId });
    // change ids to strings
    relics.forEach(relic => relic.id = relic.id.toString());
    ctx.response.body = relics;
    ctx.response.status = 200;
});

relicRouter.get('/:id', async (ctx) => {
    const userId = ctx.state.user._id;
    const relic = await relicStore.findOne({ id: ctx.params.id });
    const response = ctx.response;
    if (relic) {
        if (relic.userId === userId) {
            response.body = relic;
            response.status = 200;
        } else {
            response.body = { message: 'Unauthorized User' };
            response.status = 403;
        }
    }
    else {
        response.body = { message: `relic with id ${ctx.params.id} not found` };
        response.status = 404;
    }
});

const createRelic = async (ctx, relic, response) => {
    try {
        relic.userId = ctx.state.user._id;
        console.log(relic);
        const newRelic = await relicStore.insert(relic);
        console.log(newRelic);
        response.body = newRelic;
        response.status = 201;
        broadcast(relic.userId, { type: 'created', payload: relic });
    } catch (err) {
        console.log(err);
        response.body = { message: err.message };
        response.status = 400;
    }
};

relicRouter.post('/', async (ctx) => {
    await createRelic(ctx, ctx.request.body, ctx.response);
});

relicRouter.put('/:id', async (ctx) => {
    const relic = ctx.request.body;
    const id = ctx.params.id;
    const relicId = relic.id;
    const response = ctx.response;
    if (relicId && relicId !== id) {
        response.body = { message: 'Unauthorized User' };
        response.status = 403;
        return;
    }
    if (!relicId || relicId < 0) {
        await createRelic(ctx, relic, response);
    } else {
        const userId = ctx.state.user._id;
        relic.userId = userId;
        const updated = await relicStore.update({ id: parseInt(id) }, relic);
        if (updated === 1) {
            response.body = relic;
            response.status = 200;
            broadcast(relic.userId, { type: 'updated', payload: relic });
        } else {
            response.body = { message: `relic with id ${relicId} not found` };
            response.status = 404;
        }
    }
});

relicRouter.del('/:id', async (ctx) => {
    const userId = ctx.state.user._id;
    const relic = await relicStore.findOne({ id: ctx.params.id });
    if (relic && relic.userId !== userId) {
        ctx.response.body = { message: 'Unauthorized User' };
        ctx.response.status = 403;
        return;
    }
    await relicStore.remove({ id: ctx.params.id });
    ctx.response.body = { message: 'success' };
    ctx.response.status = 204;
    broadcast(relic.userId, { type: 'deleted', payload: relic });
});
