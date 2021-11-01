package cc.ccoder.ufs.oss.api.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date RsaKeyPair.java v1.0 2021/11/1 16:57
 */
@Data
public class RsaKeyPair implements Serializable {

    private String privateKey;

    private String publicKey;

}
