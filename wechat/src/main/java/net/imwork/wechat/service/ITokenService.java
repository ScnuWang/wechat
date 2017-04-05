package net.imwork.wechat.service;

import net.imwork.wechat.entity.model.Ttoken;

public interface ITokenService {

	Ttoken getTToken(Integer tokenType);

	Ttoken updateToken(Ttoken token);
	
}
