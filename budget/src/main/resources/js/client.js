'use strict';

const rest = require('rest');
const defaultRequest = require('rest/interceptor/defaultRequest');
const mime = require('rest/interceptor/mime');
const errorCode = require('rest/interceptor/errorCode');


module.exports = rest
	.wrap(errorCode);