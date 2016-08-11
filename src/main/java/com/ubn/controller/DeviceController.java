package com.ubn.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubn.constant.ConfingConstants;
import com.ubn.dto.DeviceDTO;
import com.ubn.dto.PublickeyDTO;
import com.ubn.dto.PurchaseDTO;
import com.ubn.dto.RODTO;
import com.ubn.dto.ROFormDTO;
import com.ubn.dto.StatusDTO;
import com.ubn.dto.TokenDTO;

@RestController
public class DeviceController {

	private RestTemplate restTemplate = new RestTemplate();

	private String drm_url = ConfingConstants.DOMAIN_URL + "/" + ConfingConstants.SERVICE_NAME;
	private String token_url = ConfingConstants.DOMAIN_URL2 + "/" + ConfingConstants.SERVICE_NAME2;
	private String cara_url = ConfingConstants.DOMAIN_URL3 + "/" + ConfingConstants.SERVICE_NAME3;

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceController.class);

	@RequestMapping(value = {"/registerDevice"}, method = {RequestMethod.POST})
	public @ResponseBody DeviceDTO registerDevice(@RequestBody ROFormDTO roFormDTO) {
		DeviceDTO deviceDTO = new DeviceDTO();
		LOGGER.debug("Grandmaster delivery registerDevice deviceId=" + roFormDTO.getDeviceId());

		TokenDTO tokenDTO = new TokenDTO();
		tokenDTO.setToken(roFormDTO.getToken());
		StatusDTO statusDto = restTemplate.postForObject(token_url + "/checkToken", tokenDTO, StatusDTO.class);
		if (statusDto.getReturnCode() != 0) {
			deviceDTO.setReturnCode(1);
			deviceDTO.setReturnMessage("Not a correct token!");
		} else {
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("deviceId", roFormDTO.getDeviceId());

			deviceDTO = restTemplate.postForObject(cara_url + "/registerDevice", map, DeviceDTO.class);
		}
		return deviceDTO;
	}

	@RequestMapping(value={"/getRO"}, method={RequestMethod.POST})
	public @ResponseBody RODTO getRO(@RequestBody ROFormDTO roFormDTO) {
		RODTO dto = new RODTO();

		String deviceId = roFormDTO.getDeviceId();
        LOGGER.debug("deviceId===>"+deviceId);
		
		TokenDTO tokenDTO = new TokenDTO();
		tokenDTO.setToken(roFormDTO.getToken());
		StatusDTO statusDto = restTemplate.postForObject(token_url + "/checkToken", tokenDTO, StatusDTO.class);
		if (statusDto.getReturnCode() != 0) {
			dto.setReturnCode(1);
			dto.setReturnMessage("Not a correct token!");
		} else {
			String[] pieces = roFormDTO.getToken().split("\\.");

			byte[] byteArray = Base64.decodeBase64(pieces[1]);
			String payload_json = new String(byteArray);

			LOGGER.info(payload_json);

			HashMap<String, Object> payload_map = new HashMap<String, Object>();
			ObjectMapper mapper = new ObjectMapper();
			try {
				payload_map = (HashMap) mapper.readValue(payload_json, new TypeReference<Map<String, String>>() {});
				
			} catch (IOException e) {
				e.printStackTrace();
				dto.setReturnCode(-1);
				dto.setReturnMessage(e.getMessage());
				return dto;
			}
			
			PurchaseDTO purchaseDTO = new PurchaseDTO();
			purchaseDTO.setsDuration_type("YES");
			purchaseDTO.setsStart_datetime("2016-05-12T00:00:00");
			purchaseDTO.setsEnd_datetime("2016-12-31T00:00:00");
			purchaseDTO.setsOffline_allowed("YES");
			purchaseDTO.setsOffline_counts_allowed("10");

			PublickeyDTO publickeyDTO = restTemplate.getForObject(cara_url + "/getPublicKey/" + deviceId, PublickeyDTO.class);
			LOGGER.debug("publickeyDTO.getReturnCode()===>"+publickeyDTO.getReturnCode());
			if (publickeyDTO.getReturnCode() == 0) {
				LOGGER.debug("publickeyDTO.getPublickey())===>"+publickeyDTO.getPublickey());
				roFormDTO.setPublickey(publickeyDTO.getPublickey());
				roFormDTO.setPurchaseDTO(purchaseDTO);

				dto = restTemplate.postForObject(drm_url + "/getRO", roFormDTO, RODTO.class);
				LOGGER.info(dto.getRo());
			} else {
				dto.setReturnCode(3);
				dto.setReturnMessage("Can not get RO, because no publickey!");
			}
		}
		return dto;
	}
}
