package com.taogen.commons.collection;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CollectionUtilsTest {

    @Test
    void convertListToTree() {
        Node node = new Node(1, null, "root");
        Node node1 = new Node(2, 1, "node1");
        Node node2 = new Node(3, 1, "node2");
        Node node3 = new Node(4, null, "node3");
        Node node4 = new Node(5, 4, "node4");
        List<Node> list = Arrays.asList(node, node1, node2, node3, node4);
        CollectionUtils.convertListToTree(list, Node::getId, Node::getParentId, Node::getChildren, Node::setChildren);
        assertEquals(2, node.getChildren().size());
        assertEquals(1, node3.getChildren().size());
        assertNull(node1.getChildren());
        assertNull(node2.getChildren());
        assertNull(node4.getChildren());
    }

    @Test
    void convertTreeToList() {
        Node node = new Node(1, null, "root");
        Node node1 = new Node(2, 1, "node1");
        Node node2 = new Node(3, 1, "node2");
        node.setChildren(Arrays.asList(node1, node2));
        Node node3 = new Node(4, null, "node3");
        Node node4 = new Node(5, 4, "node4");
        node3.setChildren(Arrays.asList(node4));
        List<Node> list = Arrays.asList(node, node3);
        List<Node> result = CollectionUtils.convertTreeToList(list, Node::getChildren);
        assertNotNull(result);
        assertEquals(5, result.size());
        System.out.println(result);
    }

    @Test
    void convertTreeToList2() {
        Node node = new Node(1, null, "root");
        Node node1 = new Node(2, 1, "node1");
        Node node2 = new Node(3, 1, "node2");
        node.setChildren(Arrays.asList(node1, node2));
        Node node3 = new Node(4, null, "node3");
        Node node4 = new Node(5, 4, "node4");
        node3.setChildren(Arrays.asList(node4));
        List<Node> list = Arrays.asList(node, node3);
        List<Node> result = CollectionUtils.convertTreeToList2(list, Node::getChildren);
        assertNotNull(result);
        assertEquals(5, result.size());
        System.out.println(result);
    }

    public static class Node {
        private Integer id;
        private Integer parentId;
        private String name;
        private List<Node> children;

        public Node(Integer id, Integer parentId, String name) {
            this.id = id;
            this.parentId = parentId;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "id=" + id +
                    ", parentId=" + parentId +
                    ", name='" + name + '\'' +
                    ", children=" + children +
                    '}';
        }

        public Integer getId(Node node) {
            return node.getId();
        }

        public Integer getParentId(Node node) {
            return node.getParentId();
        }

        public List<Node> getChildren(Node node) {
            return node.getChildren();
        }

        public void setChildren(Node node, List<Node> children) {
            node.setChildren(children);
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getParentId() {
            return parentId;
        }

        public void setParentId(Integer parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Node> getChildren() {
            return children;
        }

        public void setChildren(List<Node> children) {
            this.children = children;
        }
    }
}
