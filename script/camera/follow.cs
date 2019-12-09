using UnityEngine;

public class follow : MonoBehaviour
{
    public Rigidbody player;
    public Vector3 vector3;
    // Update is called once per frame
    void Update()
    {
        transform.position = player.position + vector3;
    }
}
