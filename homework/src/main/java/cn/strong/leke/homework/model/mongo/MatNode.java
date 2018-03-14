package cn.strong.leke.homework.model.mongo;

import java.util.List;

public class MatNode {

	private Long nodeId;
	private String nodeName;
	private List<KnoledgeNode> KnoledageNodes;
	/**
	 * @return the nodeId
	 */
	public Long getNodeId() {
		return nodeId;
	}
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}
	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	/**
	 * @return the knoledageNodes
	 */
	public List<KnoledgeNode> getKnoledageNodes() {
		return KnoledageNodes;
	}
	/**
	 * @param knoledageNodes the knoledageNodes to set
	 */
	public void setKnoledageNodes(List<KnoledgeNode> knoledageNodes) {
		KnoledageNodes = knoledageNodes;
	}

	
	
}
